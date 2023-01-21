<template>

  <v-container>
    <h1>check card view</h1>

    <v-row>

      <v-col
          v-for="card in cards"
          v-bind:key="card.id"
          cols="2"
      >
        <v-img
            :src="`/dixit/api/resource/card/${card.filename}`"
            @click="selectCard(card)"
            cover
        >

        </v-img>

      </v-col>

    </v-row>
  </v-container>
  <v-dialog
    v-model="showDialog"
  >
    <v-card>
      <v-img
          :src="`/dixit/api/resource/card/${selectedCard.filename}`"
      >

      </v-img>
      <v-card-actions>
        <v-btn
            :disabled="!selected"
            @click.once="submitCard"
        >
          submit
        </v-btn>
      </v-card-actions>
    </v-card>

  </v-dialog>
</template>

<script lang="ts">
import {defineComponent, onMounted, onUnmounted, ref,} from 'vue';
import type {Ref} from 'vue'
import axios from "axios";
import {useRouter} from "vue-router";
import {UserHolder} from "@/components/UserHolder";
import {DixitWebSocket} from "@/components/WebSocket";
import {SocketMessage} from "@/types/SocketMessage";

export default defineComponent({
  name: "CheckCardView",
  components: {},
  created() {

  },
  computed: {},
  props: {},
  setup() {
    const router = useRouter()

    const cards: Ref<any[]> = ref([])
    const selected = ref(false)
    let selectedCard: any = null
    const showDialog = ref(false)

    const selectCard = (card: any) => {
      selectedCard.isTarget = false
      selectedCard = card
      selectedCard.isTarget = true
      showDialog.value = true
    }

    const submitCard = () => {
      const url = `/dixit/api/play/select?userId=${UserHolder.id}&cardId=${selectedCard.id}`
      axios.post(url)
          .then((response) => {
            if (response.data === 'SELECTED_CARD') {
              DixitWebSocket.send('SELECTED_CARD')
            }
          })
    }

    const finishGame = (message: SocketMessage) => {
      if (message.action == 'SELECTED_CARD') {
        router.push(`/display-card?userId=${UserHolder.id}`)
      }
    }

    onMounted(() => {
      axios.get(`/dixit/api/play/card`)
          .then((response) => {
            cards.value = response.data
          })

      DixitWebSocket.addMessageListener(finishGame)
    })

    onUnmounted(() => {
      DixitWebSocket.removeMessageListener(finishGame)
    })


    return {
      selectCard,
      selectedCard,
      cards,
      submitCard,
      selected,
      showDialog,
    }
  },
})
</script>

<style scoped>

</style>