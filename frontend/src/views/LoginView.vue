<template>
  <input
      v-model="userId"
  />
  <v-btn
    @click="submit"
  ></v-btn>
</template>

<script>
import axios from 'axios'

export default {
  name: "LoginView",
  data() {
    return {
      userId: null,
    }
  },
  methods: {
    submit() {

      axios.post("/api/user/login", {
        userId: this.userId,
      }).then((response) => {
        console.log(response)
        this.$storage.setStorageSync("userId", this.userId);
        console.log('test!!')
      })
    }
  }
}
</script>

<style scoped>

</style>