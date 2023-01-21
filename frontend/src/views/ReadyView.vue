<template>

  <v-container
      align="center"
  >
    <user-ready-status-list/>

    <v-btn
        @click="ready"
        size="x-large"
        :disabled="submitting"
        :color="readyButtonColor"
    >
      ready
    </v-btn>
  </v-container>
</template>

<script lang="ts">
import {defineComponent, onMounted, onUnmounted, ref} from 'vue'
import axios from "axios"
import {useRouter} from "vue-router"
import {DixitWebSocket} from '@/components/WebSocket'
import UserReadyStatusList from '@/components/UserReadyStatusList.vue'
import {SocketMessage} from "@/types/SocketMessage";
import {UserHolder} from "@/components/UserHolder";

export default defineComponent({
  name: "readyView",
  components: {UserReadyStatusList},
  props: {
    id: {
      type: String,
      required: true
    },
  },
  setup() {
    const router = useRouter()

    const user = ref(null)
    const users = ref(null)
    const readyStatus = ref('WAITING')
    const submitting = ref(false)
    const readyButtonColor = ref('primary')

    const toggleReadyStatus = (readyStatus: string) => {
      switch (readyStatus) {
        case 'WAITING':
          return 'READY'
        case 'READY':
          return 'WAITING'
      }
      return ''
    }

    const getSocketAction = (readyStatus: string) => {
      switch (readyStatus) {
        case 'WAITING':
          return 'UNREADY'
        case 'READY':
          return 'READY'
      }
      return ''
    }


    const toggleButtonColor = (buttonColor: string) => {
      switch (buttonColor) {
        case 'primary':
          return ''
        case '':
          return 'primary'
      }
      return ''
    }

    const ready = () => {
      submitting.value = true
      const nextReadyStatus = toggleReadyStatus(readyStatus.value)
      axios.put(`/dixit/api/play/ready/${nextReadyStatus === 'READY'}?userId=${UserHolder.id}`)
          .then((response) => {
            console.log(response.data)
            submitting.value = false
            if (response.data === 'CHECK_CARD') {
              DixitWebSocket.send('START')
              router.push(`/check-card?userId=${UserHolder.id}&isLeader=${UserHolder.isLeader}`)
            } else {
              DixitWebSocket.send(getSocketAction(nextReadyStatus))
              readyStatus.value = nextReadyStatus
              readyButtonColor.value = toggleButtonColor(readyButtonColor.value)
            }
          })
    }

    const changeReadyStatus = (message: SocketMessage) => {
      if (message.action === 'START') {
        router.push(`/check-card?userId=${UserHolder.id}&isLeader=${UserHolder.isLeader}`)
      }
    }

    onMounted(() => {
      DixitWebSocket.addMessageListener(changeReadyStatus)

    })

    onUnmounted(() => {
      DixitWebSocket.addMessageListener(changeReadyStatus)
    })

    return {
      ready,
      user,
      users,
      readyStatus,
      submitting,
      readyButtonColor
    }
  },
})
</script>

<style scoped>

</style>