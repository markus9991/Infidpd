import sqlite3
import argparse
import json
import time, calendar
import plotly.offline as pl
import plotly.graph_objs as go

def getDay(date):
    d=time.localtime(date)
    return int(time.strftime("%d",d))

def getMonth(date):
    d=time.localtime(date)
    return int(time.strftime("%m",d))

def getYear(date):
    d=time.localtime(date)
    return int(time.strftime("%y",d))

def seperateMonths(data):
    mondata=[]
    for d in data:
        newlist=True
        for mon in mondata:
            if getMonth(d[0])==getMonth(mon[0][0]) and getYear(d[0])==getYear(mon[0][0]):
                mon.append(d)
                newlist=False
                break
        if newlist:  
            mondata.append([d])     
    return mondata

def getGradient(data):
    #es braucht mindestens zwei Messwerte an verschiedenen Tagen um die Steigung zu berechnen
    if len(data)<=2 or getDay(data[-1][0])==getDay(data[0][0]):
        #Steigung kann nicht berechnet werden
        return -1
    k=(data[-1][1]-data[0][1])/(getDay(data[-1][0])-getDay(data[0][0]))
    return k

def getAbsoluteConsumption(data, k):
    start=data[0][1]-k*(getDay(data[0][0])-1)
    monthRange = calendar.monthrange(getYear(data[0][0]),getMonth(data[0][0]))
    end=data[0][1]+k*(monthRange[1]-getDay(data[0][0]))
    return end-start

def getAVG(data):
    summe=0
    for value in data:
        summe+=value
    return summe/len(data)
#read database path from config file
ap = argparse.ArgumentParser()
ap.add_argument("-c", "--config", required=True, help="path to the JSON config file")
args = vars(ap.parse_args())
config = json.load(open(args["config"]))

#fetch data from database    
conn=sqlite3.connect(config["database"])
sql="SELECT * FROM "+config["table"]
curs=conn.cursor()
curs.execute(sql)
result=curs.fetchall()
conn.close()

#prepare data for plotting
months=seperateMonths(result)
x=[]
y=[]

for l in months:
    if(getYear(l[0][0])==config["year"]):
        k=getGradient(l)
        if k==-1:      
            continue
        x.append(time.strftime("%B %y",time.localtime(l[0][0])))
        y.append(getAbsoluteConsumption(l, k))

y.append(getAVG(y))
x.append("Durchschnittlicher Verbrauch")

layout = go.Layout(
    annotations=[
        dict(x=xi,y=yi,
             text=str(yi),
             xanchor='center',
             yanchor='bottom',
             showarrow=False,
        ) for xi, yi in zip(x, y)]
)

#plotting   
fig=go.Figure(data=[go.Bar(x=x,y=y)],layout=layout)  
pl.plot(fig,image="png")


