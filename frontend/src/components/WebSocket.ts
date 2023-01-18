import SockJS from 'sockjs-client'

let socket: any = null
let userId: string | null
const onMessageListeners: Function[] = []
export const DixitWebSocket = {
    send: (action: string) => {
        socket.send(JSON.stringify({
            userId: userId,
            action: action
        }));
    },
    connect() {
        socket = new SockJS("/dixit/ws")
        socket.onmessage = this.onMessage
    },

    onMessage(message: any) {
        console.log('message received!', message)
        console.log('message received!', onMessageListeners)
        onMessageListeners.forEach((onMessageListener) => {
            console.log(message.data)
            onMessageListener(JSON.parse(message.data))
        })
    },

    login(loggedInUserId: string) {
        userId = loggedInUserId
        this.send('LOGIN')
    },
    logout() {
        if (userId) {
            this.send('LOGOUT')
        }
        userId = null
    },

    addMessageListener(listener: (message: any) => any) {
        onMessageListeners.push(listener)
    },

    removeMessageListener(listener: (message: any) => any) {
        const index = onMessageListeners.indexOf(listener)

        if (index >= 0) {
            onMessageListeners.slice(index, 1)
        }
    }
}