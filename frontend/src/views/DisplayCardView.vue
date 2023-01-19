<template>

  <v-container>
    <h1>{{selectCount}}</h1>
    <v-btn
        @click="selectCard"
    >
      {{ card ? card.id : '' }}
    </v-btn>
  </v-container>
  <v-container>

    <v-btn
        @click="returnToStart"
    >
      replay
    </v-btn>
  </v-container>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";

export default defineComponent({
  name: "DisplayCardView",
  components: {},
  created() {

  },
  computed: {},
  props: {
  },
  setup() {
    const card = ref(null)
    const selectCount = ref(0)
    let lastSelected: number = new Date().getUTCMilliseconds()

    onMounted(() => {
      console.log('display card onMounted')
      axios.get(`/dixit/api/play/finish`)
          .then((response) => {
            console.log(response.data)
            card.value = response.data
          })
    })

    const returnToStart = () => {

    }

    const selectCard = () => {

      const now = new Date().getUTCMilliseconds()

      if (now < lastSelected + 500) {
        return
      }

      selectCount.value++
      lastSelected = now
    }

    return {
      card,
      returnToStart,
      selectCount,
      selectCard
    }
  },
})
</script>

<style scoped>

</style>