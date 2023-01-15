<template>

  <v-btn
      @click.once="ready"
      small
  >
    ready
  </v-btn>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
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

    const ready = () => {
      axios.put(`/api/play/ready?userId=${props.userId}`)
          .then(() => {
            const intervalId = setInterval(() => {
              axios.get(`/api/play/status`)
                  .then(response => {
                    if (response.data === 'PLAYING') {
                      clearInterval(intervalId)
                      router.push(`/check-card?userId=${props.userId}&isLeader=false`)
                    }
                  })
            }, 1000)
          })
    }
    return {
      ready
    }
  },
})
</script>

<style scoped>

</style>