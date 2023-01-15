<template>

  <v-container>
    <v-list>

      <v-list-item
          v-for="user in users"
          v-bind:key="user.id"
      >

        <v-list-item-title
            v-text="user.id"
        >
        </v-list-item-title>

        <v-list-item-action>
          <v-icon
              v-if="!user.isLeader"
              @click="changeLeader(user.id)"
          >
            mdi-swap-horizontal
          </v-icon>
          <v-icon
              v-if="user.id !== userId"
              @click="kickOutUser(user.id)"
          >
            mdi-close
          </v-icon>
        </v-list-item-action>

      </v-list-item>


    </v-list>
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

    let intervalId : number | null = null

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