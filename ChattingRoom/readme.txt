——————————————————————————————————
|	这是一个多人聊天小程序，				|
|	可以支持5人（可以通过修改参数拓展）在局域网内进行多人聊天	|
|	如果服务器有外网IP，					|
|	在服务器上运行服务端程序可以实现多人聊天（记得修改IP参数）	|
|	不受局域网限制					|
——————————————————————————————————

运行方式：
	服务端：（主类：chattingRoom）
		javac chattingRoom.java
		java chattingRoom

	客户端：（主类：ChattingRoomClient）
		javac ChattingRoomClient.java
		java ChattingRoomClient

*注意*
在聊天时请不要输入“`”符号，否则会导致结果不正确
在运行客户端前需保证服务端已正常运行，否则客户端程序会出错，需关闭cmd重新运行
如果程序运行显示无法连接服务器，请检查IP是否正确，若仍无法解决，请在任务管理器中关闭java SE的任务

//由于时间匆忙，程序没有较好的注释，如有疑问请联系machang@whu.edu.cn
//程序还存在一些问题，请关注后续跟新。。。
