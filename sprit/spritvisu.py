'''
Created on 14.03.2017

@author: Markus
'''
import argparse, json
import sqlite3
import os
import datetime, time
import plotly.offline as pl
import plotly.graph_objs as go
import utils


ap = argparse.ArgumentParser()
ap.add_argument("-c", "--config", required=True, help="path to the JSON config file")
args = vars(ap.parse_args())
config = json.load(open(args["config"]))
dt=config["today"].split("-")
today=datetime.datetime(int(dt[0]),int(dt[1]),int(dt[2]))

#open connection to database
conn=sqlite3.connect(config["database"])
curs=conn.cursor()

#fetch all gas station
curs.execute(config["sql1"])
result=curs.fetchall()

#fetch average prizes for the last year
datayear=[]
for x in result:
    variables=(x[1],(today-datetime.timedelta(days=365)),today)
    curs.execute(config["sql2"],variables)
    datayear.append(curs.fetchall())

#fetch the cheapest gas stations of today    
today=(today+datetime.timedelta(hours=12))
variables=((today-datetime.timedelta(days=1)).strftime("%Y-%m-%d %H"),today.strftime("%Y-%m-%d %H"))
curs.execute(config["sql3"],variables)
dataday=curs.fetchall()
    
#prepare datayear for plotting 
visudata=[]
for eintrag in datayear:
    x=[]
    y=[]
    for e in eintrag:
        x.append(e[0])
        y.append(e[1])
    visudata.append(go.Scatter(x=x,y=y,name=eintrag[0][2]))    
layout = dict(title = 'Durchschn. Spritverbrauch des letzten Jahres', xaxis = dict(title = 'Datum'), yaxis = dict(title = 'Preis [\u20ac]'),) 
figabs=go.Figure(data=visudata,layout=layout)

#prepare daily prices for plotting
daystring=" "
for st in dataday:
    daystring+=" "+st[0].split(" ")[1]+" Uhr | "+str(st[1])+"\u20ac | "+str(st[2])+"\n"
conn.close()

#plot the images
pl.plot(figabs,image="png" , image_filename='year',image_width=1500)
time.sleep(5)
utils.text2png(daystring,config["path"]+"day.png", fontfullpath = "Calibri.ttf", heading="Billigste Tankstellen("+today.strftime("%Y-%m-%d")+"):",headingfontsize=26,headingpaddingleft=0,fontsize=22,leftpadding = 60, toppadding = 30, width=400)

#merge the plotted images and remove the parts
utils.merge_images(config["path"]+"year.png", config["path"]+"day.png", "visu.png")
os.remove(config["path"]+"year.png")
os.remove(config["path"]+"day.png")
print("done")