<template>

  <v-container
      align="center"
  >

    <v-btn
        v-if="user && user.isLeader"
        @click.once="start"
        :disabled="(readyStatus !== 'ALL') || submitting"
        x-large
    >
      start
    </v-btn>
    <v-btn
        v-else-if="user && !user.isLeader"
        @click.once="ready"
        x-large
    >
      ready
    </v-btn>
  </v-container>
</template>

<script lang="ts">
import {defineComponent, onMounted, onUnmounted, ref} from 'vue';
import axios from "axios";
import {useRouter} from "vue-router";

export default defineComponent({
  name: "readyView",
  components: {},
  props: {
    userId: {
      type: String,
      required: true
    },
  },
  setup(props) {
    const router = useRouter()

    const user = ref(null)

    const getMyInfo = () => {
      return new Promise((resolve, reject) => {
        axios.get(`/dixit/api/user/${props.userId}`)
            .then((response) => {
              user.value = response.data
              resolve(response.data.isLeader)
            }).catch(() => {
              reject()
        })
      })
    }

    const ready = () => {
      axios.put(`/dixit/api/play/ready?userId=${props.userId}`)
          .then(() => {
            const intervalId = setInterval(() => {
              axios.get(`/dixit/api/play/status`)
                  .then(response => {
                    if (response.data === 'PLAYING') {
                      clearInterval(intervalId)
                      router.push(`/check-card?userId=${props.userId}&isLeader=false`)
                    }
                  })
            }, 1000)
          })
    }


    const readyStatus = ref('none')
    const submitting = ref(false)

    const start = () => {
      readyStatus.value = 'none'
      submitting.value = true
      axios.post(`/dixit/api/play/start?userId=${props.userId}`)
          .then(() => {
            if (intervalId) {
              clearInterval(intervalId)
            }
            router.push(`/check-card?userId=${props.userId}&isLeader=true`)
          }).catch(() => {
        submitting.value = false
      })
    }
    let intervalId: number | null = null


    const setUpUi = () => {
      getMyInfo()
          .then((isLeader) => {
            if (isLeader) {
              axios.put(`/dixit/api/play/ready?userId=${props.userId}`)
                  .then(response => {
                    readyStatus.value = response.data
                  })
                  .catch(() => {})
            }
          })
    }

    onMounted(() => {
      setUpUi()
      intervalId = setInterval(() => {
        setUpUi()
      }, 3000)
    })

    onUnmounted(() => {
      if (intervalId) {
        clearInterval(intervalId)
      }
    })

    return {
      ready,
      start,
      user,
      readyStatus,
      submitting,
    }
  },
})
</script>

<style scoped>

</style>