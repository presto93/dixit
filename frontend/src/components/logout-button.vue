<template>
  <v-btn
    @click="logout"
  >
    logout
  </v-btn>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import axios from "axios";

export default defineComponent({
  name: "HomeView",
  components: {},
  props: {
    userId: {
      type: String,
      required: true
    }
  },
  methods: {

    logout() {
      axios.post("/api/user/logout", {
        userId: this.userId,
      }).then(() => {
      })
    }
  },
})
</script>

<style scoped>

</style>