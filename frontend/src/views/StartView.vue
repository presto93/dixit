<template>

  <v-btn
      @click="start"
      small
      :disabled="(readyStatus !== 'ALL') || submitting"
  >
    start
  </v-btn>
</template>

<script lang="ts">
import {defineComponent, onBeforeUnmount, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import axios from "axios";
export default defineComponent({
  name: "StartView",
  components: {},
  props: {
    userId: {
      type: String,
      required: true
    },
  },
  setup(props) {
    const router = useRouter()

    const readyStatus = ref('none')
    const submitting = ref(false)
    let intervalId: number | null = null


    onMounted(() => {
      console.log('StartView')
      console.log(props.userId)
      intervalId = setInterval(() => {
        axios.put(`/api/play/ready?userId=${props.userId}`)
            .then(response => {
              readyStatus.value = response.data
            })
      }, 1000)
    })
    onBeforeUnmount(() => {
      if (intervalId) {
        clearInterval(intervalId)
      }
    })

    const start = () => {
      readyStatus.value = 'none'
      submitting.value = true
      axios.post(`/api/play/start?userId=${props.userId}`)
          .then(() => {
            if (intervalId) {
              clearInterval(intervalId)
            }
            router.push(`/check-card?userId=${props.userId}&isLeader=true`)
          }).catch(() => {
        submitting.value = false
      })
    }

    return {
      start,
      readyStatus,
      submitting
    }
  },
})
</script>

<style scoped>

</style>