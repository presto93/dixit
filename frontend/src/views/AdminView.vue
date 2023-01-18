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
            v-if="!user.isLeader"
            @click="changeLeader(user.id)"
        >
          mdi-swap-horizontal
        </v-icon>
      </v-col>

      <v-col

          align="center"
      >

        <v-icon
            v-if="user.id !== userId"
            @click="kickOutUser(user.id)"
        >
          mdi-close
        </v-icon>
      </v-col>

    </v-row>


  </v-container>
</template>

<script lang="ts">
import type {Ref} from 'vue'
import {defineComponent, onMounted, onUnmounted, ref} from 'vue';
import axios from "axios";
import {DixitWebSocket} from "@/components/WebSocket";

export default defineComponent({
  name: "DisplayCardView",
  components: {},
  created() {

  },
  computed: {},
  props: {
    userId: {
      type: String,
      required: true
    },
  },
  setup() {
    const users: Ref<any[]> = ref([])

    const getUsers = () => {
      axios.get(`/dixit/api/user`)
          .then((response) => {
            console.log(response.data)
            users.value = response.data
          })
    }

    const updateUser = (message: any) => {
      console.log('updateUser', message)
      console.log('updateUser', users.value)
      if (message.action === 'LOGIN') {
        console.log('updateUser, log action!')
        users.value.push({
          id: message.userId,
          isLeader: message.isLeader
        })
      }

      for (let index = 0; index < users.value.length; index++) {
        if (users.value[index].id === message.userId) {
          switch (message.action) {
            case 'LOGOUT': {
              users.value.slice(index, 1)
            }
              break
            case 'LEADER': {
              users.value[index].isLeader = message.isLeader
            }
              break
          }
          break
        }
      }
    }
    const kickOutUser = (userId: string) => {

      axios.put(`/dixit/api/user/kick-out`, {
        userId: userId
      })
          .then(() => {
          })
    }

    const changeLeader = (userId: string) => {
      axios.put(`/dixit/api/user/change-leader?userId=${userId}`)
          .then(() => {
          })
    }

    onMounted(() => {
      getUsers()
      DixitWebSocket.addMessageListener(updateUser)
    })

    onUnmounted(() => {

      DixitWebSocket.removeMessageListener(updateUser)
    })


    return {
      users,
      kickOutUser,
      changeLeader,
    }
  },
})
</script>

<style scoped>

</style>