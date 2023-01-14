<template>
  <v-container>
    <v-layout
      v-if="userId"
    >
      <h1>logged in!!</h1>

    </v-layout>
    <v-layout
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
        >submit</v-btn>
      </v-card>
    </v-layout>

  </v-container>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import axios from "axios";

export default defineComponent({
  name: "HomeView",
  components: {},
  beforeUnmount() {

    axios.post("/api/user/logout", {
      userId: this.userId,
    }).then(() => {
      this.userId = null
    })
  },
  data() {
    return {
      userId: null,
      userIdInput: null
    }
  },
  computed: {
  },
  methods: {

    login() {
      axios.post("/api/user/login", {
        userId: this.userIdInput,
      }).then(() => {
        this.userId = this.userIdInput
      })
    }
  },
})
</script>

<style scoped>

</style>