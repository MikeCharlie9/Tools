# coding=utf-8
import requests
from prettytable import PrettyTable #美化库，PrettyTable模块可以将输出内容如表格方式整齐地输出
from stations import stations
# from pprint import pprint #用于打印 Python 数据结构,输入格式整齐便于阅读


pt = PrettyTable()  #以表格形式显示


def get_stations_key(value):
    key = list(stations.keys())[list(stations.values()).index(value)]  #参考链接https://blog.csdn.net/ywx1832990/article/details/79145576
    return key

def add_price(one,pt):
    header = {
        "User-Agent": "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36"
    }
 
    train_no = one['train_no']  # 火车编号(非车次)
    from_station_no = one['from_station_no']   # 出发地编号
    to_station_no = one['to_station_no']  # 目的地编号
    seat_type = one['seat_types']  # 座次类型，普快：1413, 高铁：090M,OM9  Z开头：113，动车：0F
    train_date = one['train_date']  # 出发日期
    train_date = ('{0}-{1}-{2}').format(train_date[:4], train_date[4:6], train_date[6:8]) #如日期20180830变为2018-08-30

    url = ("https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no={0}&from_station_no={1}&to_station_no={2}&seat_types={3}&train_date={4}").format(train_no,from_station_no,to_station_no,seat_type,train_date)
    # print(url)
    # https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no=4e000G458404&from_station_no=01&to_station_no=10&seat_types=OM9&train_date=2019-02-17
    web_data = requests.get(url, headers=header)
    pricedata = web_data.json()["data"]  #里面以字典形式保存价格等信息

    wuzuo = '-' if 'WZ' not in pricedata.keys() else pricedata['WZ']  # 高铁,动车,普快无座key值一样
    erdeng = '-' if 'O' not in pricedata.keys() else pricedata['O']  #高铁，动车二等座key值一样
    yideng = '-' if 'M' not in pricedata.keys() else pricedata['M']
    shangwu = '-' if 'A9' not in pricedata.keys() else pricedata['A9']
    tedeng = '-' if 'P'not in pricedata.keys() else pricedata['P']
    # 动车( WZ:无座, O:二等, F:动卧 )
    gaojidongwo = '-' if 'A6' not in pricedata.keys() else pricedata['A6']
    dongwo = '-' if 'F' not in pricedata.keys() else pricedata['F']
    # 普快( WZ:无座, A1:硬座, A3:硬卧, A4:软卧 )
    yingzuo = '-' if 'A1' not in pricedata.keys() else pricedata['A1']
    yingwo = '-' if 'A3' not in pricedata.keys() else pricedata['A3']
    ruanwo = '-' if 'A4' not in pricedata.keys() else pricedata['A4']

    pt.add_row(["",   # 车次
        "",           # 出发地
        "",           # 目的地
        "",
        "",
        "",
        shangwu,      # 商务座
        tedeng,       # 特等座
        yideng,       # 一等座
        erdeng,       # 二等座
        gaojidongwo,  # 高级动卧
        dongwo,       # 动卧
        ruanwo,       # 软卧
        yingwo,       # 硬卧
        yingzuo,      # 硬座
        wuzuo,        # 无座
        ""            # 备注
        ])

# def get_input():
#     from_chinese = input("输入出发地:\n")
#     to_chinese = input("输入目的地:\n")
#     date = input("输入出发日(如2018-07-25,月份前面必须加0):\n")
#     # from_chinese="武汉"
#     # to_chinese="上海"
#     # date="2019-02-17"
#     from_station = stations[from_chinese]
#     to_station = stations[to_chinese]
#     print(from_station, "->", to_station)
#     url ="https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station="+from_station+"&leftTicketDTO.to_station="+to_station+"&purpose_codes=ADULT"
#     return url

if __name__ == "__main__":
    try:
        # querydate="2019-2-17"
        # from_station="WHN"
        # to_station="SHH"
        # url = ("https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date={0}&leftTicketDTO.from_station={1}&leftTicketDTO.to_station={2}&purpose_codes=ADULT").format(querydate, from_station, to_station)
        # "queryZ" return 200 while "queryA" return 302
        
        # url = "https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date=2019-02-17&leftTicketDTO.from_station=WHN&leftTicketDTO.to_station=SHH&purpose_codes=ADULT"
            #主要是获取user-agent，伪装成浏览器，其它的可要，可不要
        from_chinese = input("输入出发地(中文):\n")
        to_chinese = input("输入目的地(中文):\n")
        querydate = input("输入出发日(如2019-01-01,月份和日期如果是个位数前面必须加0):\n")
        # from_chinese="武汉"
        # to_chinese="上海"
        # date="2019-02-17"
        from_station = stations[from_chinese]
        to_station = stations[to_chinese]
        print("车站简称："+from_station, "->", to_station)
        url ="https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="+querydate+"&leftTicketDTO.from_station="+from_station+"&leftTicketDTO.to_station="+to_station+"&purpose_codes=ADULT"
        # url ="https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date="+querydate+"&leftTicketDTO.from_station="+from_station+"&leftTicketDTO.to_station="+to_station+"&purpose_codes=ADULT"
        header = {
            "User-Agent": "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36"
        }
        print("正在请求服务器...")
        web_data = requests.get(url, headers=header)  #verify=False表示不判断证书
        print("服务器请求状态码:"+str(web_data.status_code)+"\t",end="")
        if web_data.status_code == 200 :
            print("服务器连接正常")
        else :
            print("连接异常，请检查网络连接后重试")
        print("编码格式："+web_data.encoding+"\t",end="")
        if web_data.encoding == 'UTF-8' :
            print("获取服务器数据正常")
        else :
            print("获取服务器数据异常，请检查输入车站和日期的格式")
        print("正在获取余票信息，请等待...")

        yupiao_save=open("ticket_information.txt","w")

        web_data.encoding = "utf-8"
        traindatas = web_data.json()["data"]["result"]  # 返回的结果，转化成json格式，取出data中的result方便后面解析列车信息用
        # print(web_data.text)
        # pt = PrettyTable()  #以表格形式显示
        # pt._set_field_names("车次 始发站/终点站 出发地/目的地 发车时间 到达时间 历时 商务座 特等座 一等座 二等座 动卧 软卧 硬卧 硬座 无座 备注".split())  #列名称
        pt.field_names = ("车次 出发地 目的地 发车时间 到达时间 历时  商务座 特等座 一等座 二等座 高级动卧 动卧 软卧 硬卧 硬座 无座 备注".split())
        # /起点站/终点站
        url_param_list = []   # 其中保存请求车票价格所需拼接url的参数信息，如[["train_no":690000K2380B,"from_station":"01",...]]        
        # print("车次\t始发站\t终点站\t出发地\t目的地\t出发\t到达\t花费")
        for onedata in traindatas:
            onedata = onedata.split("|")  #获取一条数据所有信息，以列表形式保存
            remarks = onedata[1]  # 备注
            checi = onedata[3]  # 车次
            start_station = onedata[4]  # 始发站
            end_station = onedata[5]    # 终点站
            start_station_key = get_stations_key(start_station)  # 始发站变为中文 如SZQ表示深圳站
            end_station_key = get_stations_key(end_station)  # 终点站变为中文
            from_station = onedata[6]  #出发地简称
            to_station = onedata[7]    #目的地简称
            from_station_key = get_stations_key(from_station)  # 出发地变为中文
            to_station_key = get_stations_key(to_station)  # 目的地变为中文
            departuretime = onedata[8] #出发时间
            arrivaltime = onedata[9]   #结束时间
            costtime = onedata[10]    #花费时间
            #date = onedata[13]        #查询日期

            for element in onedata:
                yupiao_save.write(element)
                yupiao_save.write("|")
            yupiao_save.write("\n")

            # 获取余票信息
            #普快
            ruanwo = onedata[-16] if onedata[-16].strip() != "" else "-"   # 软卧
            wuzuo = onedata[-13] if onedata[-13].strip() != "" else "-"  # 无座
            yingwo = onedata[-11] if onedata[-11].strip() != "" else "-"   # 硬卧
            yingzuo = onedata[-10] if onedata[-10].strip() != "" else "-"  # 硬座
            # #高铁/动车
            wuzuo = onedata[-13] if onedata[-13].strip() != "" else "-"   #无座
            erdeng = onedata[-9] if onedata[-9].strip() != "" else "-"   # 二等座
            yideng = onedata[-8] if onedata[-8].strip() != "" else "-"   # 一等座
            shangwu = onedata[-7] if onedata[-7].strip() != "" else "-"  # 商务座
            tedeng = onedata[-14] if onedata[-14].strip() != "" else "-"  # 特等座
            dongwo = onedata[-6] if onedata[-6].strip() != "" else "-"   # 动车动卧
            gaojidongwo = onedata[-18] if onedata[-18].strip() != "" else "-"   # 高级动卧

            pt.add_row([checi,
                from_station_key,#+"/"+start_station_key,#Fore.GREEN+
                to_station_key,#+"/"+end_station_key,#Fore.RED+
                departuretime,
                arrivaltime,
                costtime,

                shangwu,      # 商务座
                tedeng,       # 特等座
                yideng,       # 一等座
                erdeng,       # 二等座
                gaojidongwo,  # 高级动卧
                dongwo,       # 动卧
                ruanwo,       # 软卧
                yingwo,       # 硬卧
                yingzuo,      # 硬座
                wuzuo,        # 无座
                remarks       # 备注

                ])
            # 保存每条火车线路中与请求价格url相关的参数
            if(onedata[-2].strip() == ''):  #座位类型为空时发现是列车运行图调整，暂停发售，此时跳过
                continue
            url_param_dict = {
                'train_no': onedata[2],   # 火车编号
                'from_station_no': onedata[16],  # 出发地编号
                'to_station_no': onedata[17],   # 目的地编号
                'seat_types': onedata[-4],  # 座位类型
                'train_date': onedata[13],  # 出发日期
                'from_station_key': from_station_key,  # 记录出发地中文，方便后面展示
                'to_station_key': to_station_key,   # 记录目的地中文，方便后面展示
                'checi': checi  # 记录车次，方便后面展示
            }
            # url_param_list.append(url_param_dict)

            add_price(url_param_dict,pt)

        yupiao_save.close()
        # print("车站简称："+get_stations_key(from_station)+"->"+get_stations_key(to_station),end="")
        print("查询日期：", querydate)
        print("余票和票价已获得，正在处理数据，请等待...")
        print("共查询到"+str(len(traindatas))+"条信息")
        # 获得查询车次信息
        print(pt)
        # 获得车票价格
        # price(url_param_list)

        input("按任意键结束程序")



    except Exception as ex:
        print("程序异常，错误为%s" % ex)
