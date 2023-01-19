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
  },
  methods: {

    logout() {
      axios.post("/dixit/api/user/logout", {
        userId: UserHolder.id,
      }).then(() => {
      })
    }
  },
})
</script>

<style scoped>

</style>