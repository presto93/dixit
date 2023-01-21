<template>
  <v-app>
    <v-main
        v-if="userId"
    >
      {{ userId }}님 안녕하세요!
      <router-view/>
      <v-bottom-navigation>
        <v-btn
            @click="goToAdminPage"
            size="x-small"
        >
          admin
        </v-btn>
        <v-btn
            @click="startGame"
            size="x-small"
        >
          dixit
        </v-btn>
        <v-btn
            @click="logout"
            size="x-small"
        >
          logout
        </v-btn>
      </v-bottom-navigation>
    </v-main>

    <v-main
        v-else
    >
      <v-card id="login-area"
              align="center"
      >
        <v-text-field
            v-model="userIdInput"
            single-line
            clearable
            placeholder="username"
        />
        <v-btn
            id="login-button"
            @click="login"
            color="primary"
        >submit
        </v-btn>
      </v-card>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import {defineComponent, onMounted, onUnmounted, Ref, ref} from 'vue'
import axios from "axios";
import {useRouter} from "vue-router"
import {DixitWebSocket} from "@/components/WebSocket";
import {SocketMessage} from "@/types/SocketMessage";
import {UserHolder} from "@/components/UserHolder";

export default defineComponent({
  name: 'App',

  setup() {

    const router = useRouter()

    const userId: Ref<string | null> = ref(null)
    const userIdInput = ref(null)

    const login = () => {
      UserHolder.id = null
      UserHolder.isLeader = false

      axios.post("/dixit/api/user/login", {
        userId: userIdInput.value,
      }).then((response) => {
        userId.value = response.data.id
        UserHolder.id = response.data.id
        UserHolder.isLeader = response.data.isLeader
        userIdInput.value = null
        DixitWebSocket.login()
        router.push(`/ready`)
      })
    }

    const logout = () => {
      if (!userId.value) {
        return
      }
      DixitWebSocket.logout()
      userId.value = null
      axios.put("/dixit/api/user/logout", {
        id: userId.value,
      }).then(() => {
        userId.value = null
        router.push(`/`)
      }).catch(() => {
        router.push(`/`)
      })
    }

    const startGame = () => {
      router.push(`/ready`)
    }

    const goToAdminPage = () => {
      router.push(`/admin`)
    }

    const updateUser = (message: SocketMessage) => {
      if (message.userId !== userId.value) {
        return
      }
      switch (message.action) {
        case 'LOGOUT': {
          UserHolder.id = null
          UserHolder.isLeader = false
          userId.value = null
        }
      }
    }

    onMounted(() => {
      window.addEventListener("unload", logout)
      DixitWebSocket.connect()
      DixitWebSocket.addMessageListener(updateUser)
    })

    onUnmounted(() => {
      DixitWebSocket.removeMessageListener(updateUser)
    })

    const overlay = ref(true)
    const overlay2 = ref(true)

    const test = () => {
      overlay.value = !overlay.value
      console.log(overlay.value)
    }

    return {
      userId,
      userIdInput,
      login,
      logout,
      startGame,
      goToAdminPage,
      test,
      overlay,
      overlay2,
    }
  },

  data() {
    return {}
  },
  methods: {},
})
</script>

<style scoped>

#login-area {
  margin: 50px;
  padding: 50px;
}

#login-area > * {
  margin: 10px 0;
}

</style>