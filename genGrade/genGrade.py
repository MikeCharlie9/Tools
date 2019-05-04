# -*- coding: utf-8 -*-
"""
Created on Sta May 4 08:55:52 2019

@author: MaChang
"""

import requests
import json
import urllib

#一个简单的爬虫小游戏
#这个程序用来生成2019年交大五一长跑节中参赛选手的参赛证明并保存为图片

#请求的url
url="http://mpmsapi.51ydb.cn/v1/match/findmatchcert?timestamp=1556942053"
#模拟浏览器的请求头
header = {"User-Agent": "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36"}
#从name这个编号开始请求
name=1
#获得总共100个选手的参赛证明
for i in range(100):   
#    这里获得的是以A开头的选手的成绩，其它的可以修改一下同样获得
    wd="A"+str(name).zfill(3)
#    以post形式发送请求，data打包
    loadData={'mid': 379,'matchid': 379,'keywords': wd,'seosource': 'null'}        
    r=requests.post(url,data=json.dumps(loadData),headers=header)  
#    获得需要的数据，cerurl是证书的连接
    tran=r.json()["data"]["cerurl"]
#    将网页保存为图片，命令为号码布编号
#    保存在当前路径下的pic文件夹中，如果没有这个文件夹需要手动创建
    urllib.request.urlretrieve(tran,"./pic/"+wd+".jpg")
#    依次获得100人
    name+=1

