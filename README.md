# 采用`WebSocket`实现代替极光推送的方案示例

[TOC]

本文主要通过使用  [`nv-websocket-client`](https://github.com/TakahikoKawasaki/nv-websocket-client/tree/master/src/main/java/com/neovisionaries/ws/client) 实现了替代极光推送的方案，主要包括建立连接、消息推送、心跳保活、超时重传以及`Service`尽量保活等内容。

外卖订单推送主要逻辑：

1. 总共4种消息，新增美团订单、取消美团订单、新增饿百订单、取消饿百订单。这4中消息走socket推送通道，不会再发极光推送了。

2. 当前逻辑是按门店推送，没有按设备（这里指 pos 机）推送。也就是说：当 A门店 有订单 消息1，api 会给 A门店 所有在线的设备都推送 消息1 。如果 A门店 任意一个设备回复接收到 消息1 了，api 就认为这条 消息1 已经推出去了。这时，即使 A门店 另一台设备没有收到，也不会推 消息1 给另一台设备了。

3. 离线消息逻辑。如果api将 A门店 的 消息1 推出去后，A门店 没有一台设备回复收到 消息1，api 将 消息1 视为离线消息。在离线消息的有效期内（目前是30分钟），如果有 A门店 的设备连接 socket，api 会将 消息1 推送给该设备，如果该设备回复收到 消息1，那么 api 认为 消息1 已经推送出去了。如果 A门店 没有设备回复收到 消息1，那么api认为 消息1 未推送出去，即还是离线消息。

参考：

[Android通过socket长连接实现推送](https://www.jianshu.com/p/0776dac9e3a3)

[WebSocket安卓客户端实现详解(一)--连接建立与重连](https://blog.csdn.net/zly921112/article/details/72973054)

[WebSocket安卓客户端实现详解(二)--客户端发送请求](https://blog.csdn.net/zly921112/article/details/76758424)

[WebSocket安卓客户端实现详解(三)--服务端主动通知](https://blog.csdn.net/zly921112/article/details/76767876)

