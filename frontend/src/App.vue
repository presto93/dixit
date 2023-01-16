<template>
  <v-app>
    <v-main
        v-if="userId"
    >
      <router-view/>
      <v-bottom-navigation>

        <v-btn
            @click="goToAdminPage"
            x-small
        >
          admin
        </v-btn>
        <v-btn
            @click="startGame"
            x-small
        >
          dixit
        </v-btn>
        <v-btn
            @click="logout"
            x-small
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
import {defineComponent, onMounted, ref} from 'vue'
import axios from "axios";
import {useRouter} from "vue-router";

export default defineComponent({
  name: 'App',

  setup() {

    const router = useRouter()

    const userId = ref(null)
    const userIdInput = ref(null)

    onMounted(() => {
      window.addEventListener("unload", logout)
      let connection: WebSocket | null = null

      console.log("Starting connection to WebSocket Server");
      connection = new WebSocket('http://localhost:8080');

      connection.onmessage = (event) => {
        console.log("look, I got something from server");
        console.log(event.data);
      };

      connection.onopen = (event) => {
        console.log(event);
        console.log("Successfully connected to the echo websocket server...");
      };

    })

    const login = () => {
      axios.post("/dixit/api/user/login", {
        userId: userIdInput.value,
      }).then((response) => {
        userId.value = response.data.id
        userIdInput.value = null

        router.push(`/ready?userId=${userId.value}`)
      })
    }

    const logout = () => {
      if (!userId.value) {
        return
      }
      axios.put("/dixit/api/user/logout", {
        userId: userId.value,
      }).then(() => {
        userId.value = null
        router.push(`/`)
      }).catch(() => {

        router.push(`/`)
      })
    }

    const startGame = () => {
      router.push(`/ready?userId=${userId.value}`)
    }

    const goToAdminPage = () => {
      router.push(`/admin?userId=${userId.value}`)
    }

    return {
      userId,
      userIdInput,
      login,
      logout,
      startGame,
      goToAdminPage,
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