import SockJS from 'sockjs-client'
import {SocketMessage} from "@/types/SocketMessage";
import {UserHolder} from "@/components/UserHolder";

let socket: any = null
const onMessageListeners: ((SocketMessage) => any)[] = []
export const DixitWebSocket = {
    send: (action: string) => {
        socket.send(JSON.stringify({
            userId: UserHolder.id,
            action: action
        }));
    },
    connect() {
        socket = new SockJS("/dixit/ws")
        socket.onmessage = this.onMessage
    },

    onMessage(message: any) {
        onMessageListeners.forEach((onMessageListener) => {
            onMessageListener(JSON.parse(message.data))
        })
    },

    login() {
        this.send('LOGIN')
    },
    logout() {
        this.send('LOGOUT')
    },

    addMessageListener(listener: ((message: SocketMessage) => any) | (() => any)) {
        if (onMessageListeners.indexOf(listener) === -1) {
            onMessageListeners.push(listener)
        }
    },

    removeMessageListener(listener: ((message: SocketMessage) => any) | (() => any)) {
        const index = onMessageListeners.indexOf(listener)

        if (index >= 0) {
            onMessageListeners.slice(index, 1)
        }
    }
}