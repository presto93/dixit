<template>
  <v-app>
    <v-main
        v-if="userId"
    >
      <v-container>

        <router-view/>
      </v-container>
      <v-container>

        <v-btn
            @click="logout"
            small
        >
          logout
        </v-btn>
      </v-container>
    </v-main>

    <v-main
        v-else
    >
      <v-card>
        <v-text-field
            v-model="userIdInput"
            single-line
            clearable
            placeholder="username"
        />
        <v-btn
            @click="login"
            block
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
    let isLeader: boolean = false

    onMounted(() => {
      window.addEventListener("unload", logout)
    })

    const login = () => {
      axios.post("/api/user/login", {
        userId: userIdInput.value,
      }).then((response) => {
        userId.value = response.data.id
        isLeader = response.data.isLeader
        userIdInput.value = null

        if (isLeader) {
          router.push(`/start?userId=${userId.value}`)
        } else {
          router.push(`/ready?userId=${userId.value}`)
        }
      })
    }

    const logout = () => {
      if (!userId.value) {
        return
      }
      axios.put("/api/user/logout", {
        userId: userId.value,
      }).then(() => {
        userId.value = null
        router.push(`/`)
      })
    }

    return {
      userId,
      userIdInput,
      login,
      logout
    }
  },

  data() {
    return {}
  },
  methods: {},
})
</script>
