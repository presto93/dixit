<template>
  <span></span>
</template>

<script lang="ts">
export default {
  name: "WebSocket",

  setup() {
    const address = 'ws://localhost:8080'
    let message: string = ''
    let socket = new WebSocket(address)
    const connect = () => {
      socket.onopen = (data) => {
        console.log('onopen', data)
      }
      socket.onerror = (data) => {
        console.log('onerror', data)
      }
      socket.onmessage = (data) => {
        console.log('onmessage', data)
      }
      socket.onclose = (data) => {
        console.log('onclose', data)
      }
    }
    const sendMessage = () => {
      socket.send(message)
    }
    const disconnect = () => {
      if (socket && socket.readyState === 1) {
        socket.close()
      }
    }

  // eslint-disable-next-line no-undef
    defineExpose({
      connect,
      sendMessage,
      disconnect,
    })
  }
}


</script>

<style scoped>

</style>