<template>

  <v-container>
    <h1>check card view</h1>
    <v-btn
        v-for="card in cards"
        @click="selectCard(card)"
        :color="card.isTarget ? 'primary' : ''"
        v-bind:key="card.id"
        :disabled="selectedCard"
    >
      {{ card.id }}
    </v-btn>
  </v-container>
  <v-container>

    <v-btn
        :disabled="!selectedCard"
        @click="finishGame"
    >
      next step
    </v-btn>
  </v-container>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {useRouter} from "vue-router";
import {UserHolder} from "@/components/UserHolder";

export default defineComponent({
  name: "CheckCardView",
  components: {},
  created() {

  },
  computed: {},
  props: {
  },
  setup() {
    const router = useRouter()

    const cards = ref([])
    const selectedCard =  ref(false)

    console.log('CheckCardView')

    onMounted(() => {

      console.log('CheckCardView onMounted')
      axios.get(`/dixit/api/play/card`)
          .then((response) => {
            cards.value = response.data
          })

    })

    const selectCard = (card: any) => {
      const url = `/dixit/api/play/select?userId=${UserHolder.id}&cardId=${card.id}`
      axios.post(url)
          .then(() => {
            card.isTarget = true
            selectedCard.value = true
          })
    }

    const finishGame = () => {
      router.push(`/display-card?userId=${UserHolder.id}`)
    }


    return {
      selectCard,
      selectedCard,
      cards,
      finishGame
    }
  },
})
</script>

<style scoped>

</style>