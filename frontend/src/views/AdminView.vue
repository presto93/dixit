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
import {defineComponent, onMounted, onUnmounted, ref} from 'vue';
import axios from "axios";

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
  setup(props) {
    const users = ref(null)

    const getUsers = () => {
      axios.get(`/dixit/api/user`)
          .then((response) => {
            users.value = response.data
          })
    }

    const kickOutUser = (userId: string) => {

      axios.put(`/dixit/api/user/kick-out`, {
        userId: userId
      })
          .then((response) => {
            users.value = response.data
          })
    }

    const changeLeader = (userId: string) => {
      axios.put(`/dixit/api/user/change-leader?userId=${userId}`)
          .then((response) => {
            users.value = response.data
          })
    }

    let intervalId: number | null = null

    onMounted(() => {
      console.log(props.userId)
      getUsers()
      intervalId = setInterval(() => {
        getUsers()
      }, 2000)
    })

    onUnmounted(() => {
      if (intervalId) {
        clearInterval(intervalId)
      }
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