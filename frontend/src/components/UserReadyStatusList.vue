<template>

  <v-container>
    <v-row
        v-for="user in users"
        v-bind:key="user.id"
    >
      <v-col
          class="userIdDisplay"
          v-text="user.id"
          cols="8"
          align="center"
      >
      </v-col>
      <v-col
          align="center"
      >
        <v-icon
            v-if="user.isLeader"
        >
          mdi-star
        </v-icon>
      </v-col>
      <v-col
          align="center"
      >
        <v-icon
            v-if="user.playingStatus === 'READY'"
        >
          mdi-check
        </v-icon>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import type {Ref} from 'vue'
import {defineComponent, onMounted, onUnmounted, ref} from 'vue';
import axios from "axios";
import {User} from "@/types/User";
import {DixitWebSocket} from "@/components/WebSocket";
import {SocketMessage} from "@/types/SocketMessage";

export default defineComponent({
  name: "UserReadyStatusList",
  components: {},
  props: {},
  setup() {
    const users: Ref<User[]> = ref([])

    const updateUser = (message: SocketMessage) => {
      for (let index = 0; index < users.value.length; index++) {
        if (users.value[index].id === message.userId) {
          switch (message.action) {
            case 'READY': {
              users.value[index].playingStatus = 'READY'
            }
              break
            case 'UNREADY': {
              users.value[index].playingStatus = 'WAITING'
            }
              break
          }
          break
        }
      }
      if (message.action === 'LOGIN') {
        users.value.push({
          id: message.userId,
          isLeader: message.isLeader,
          playingStatus: 'WAITING',
        })
      }
    }

    onMounted(() => {
      DixitWebSocket.addMessageListener(updateUser)
      axios.get(`/dixit/api/user`)
          .then((response) => {
            users.value = response.data
          })
    })

    onUnmounted(() => {
      DixitWebSocket.removeMessageListener(updateUser)
    })

    return {
      users,
    }
  },
})
</script>

<style scoped>

</style>