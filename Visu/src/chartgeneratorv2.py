import sqlite3
import argparse
import json
import scipy.interpolate as sci
import calendar, time
import plotly.offline as pl
import plotly.graph_objs as go


def preparedata(year,data):
    res=[[],[]]
    for i in range(1,13):
        fd=calendar.timegm(time.strptime('01/'+str(i)+'/'+str(year)+' 00:00:00', '%d/%m/%Y %H:%M:%S'))    
        ld=calendar.timegm(time.strptime(str(calendar.monthrange(year, i)[1])+'/'+str(i)+'/'+str(year)+' 00:00:00', '%d/%m/%Y %H:%M:%S'))
        cons=data(ld)-data(fd)
        res[0].append(time.strftime("%B %y",time.localtime(fd)))
        res[1].append(round(cons,2))
    return res

def getAVGdaily(data, year):
    res=[[],[]]
    for i in range(1,13):
        summe=0
        for j in range(1,calendar.monthrange(year,i)[1]+1):
            day=calendar.timegm(time.strptime(str(j)+'/'+str(i)+'/'+str(year)+' 00:00:00', '%d/%m/%Y %H:%M:%S'))
            if j==calendar.monthrange(year,i)[1]:
                if i==12:
                    dayu1=calendar.timegm(time.strptime('31/12/'+str(year)+' 00:00:00', '%d/%m/%Y %H:%M:%S'))
                else:
                    dayu1=calendar.timegm(time.strptime('1/'+str((i+1))+'/'+str(year)+' 00:00:00', '%d/%m/%Y %H:%M:%S'))
                
            else:    
                dayu1=calendar.timegm(time.strptime(str(j+1)+'/'+str(i)+'/'+str(year)+' 00:00:00', '%d/%m/%Y %H:%M:%S'))    
            summe+=inter(dayu1)-inter(day)     
        res[0].append(time.strftime("%B %y",time.localtime(day)))       
        res[1].append(round(summe/calendar.monthrange(year,i)[1],2))
    return res        
            
def getAVG(data):
    summe=0
    for value in data:
        summe+=value
    return round(summe/len(data),2)


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

x=[]
y=[]
for eintrag in result:
    print(time.strftime("%d/%m/%Y %H:%M:%S",time.localtime(eintrag[0])))
    x.append(eintrag[0])
    y.append(eintrag[1])

inter=sci.interp1d(x,y,kind="linear", bounds_error=False)

absdata=preparedata(2016, inter)
absdata[0].append("Durchschnittsverbrauch pro Monat")
absdata[1].append(getAVG(absdata[1]))

avgdata=getAVGdaily(inter, 2016)

layoutabs = go.Layout(
    title=config["table"],
    annotations=[dict(x=xi,y=yi, text=str(yi), xanchor='center', yanchor='bottom', showarrow=False, ) for xi, yi in zip(absdata[0], absdata[1])]
)

layoutavg = go.Layout(
    title=config["table"],
    annotations=[dict(x=xi,y=yi, text=str(yi), xanchor='center', yanchor='bottom', showarrow=False, ) for xi, yi in zip(avgdata[0], avgdata[1])]
)

#plotting   
if(config["plottype"]=="abs"):
    figabs=go.Figure(data=[go.Bar(x=absdata[0],y=absdata[1])], layout=layoutabs)
    #pl.plot(figabs,image="png")
if(config["plottype"]=="avg"):      
    figavg=go.Figure(data=[go.Bar(x=avgdata[0],y=avgdata[1])], layout=layoutavg)  
    pl.plot(figavg,image="png")

