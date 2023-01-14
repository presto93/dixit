<template>
  <h1>this is join view!!</h1>
</template>

<script>
import axios from 'axios'

export default {
  name: "SignUpView",
  created() {
    axios.post("/auth/sign-up", {
      username: 'test',
      password: '1234'
    }).then(() => {
      console.log('test!!')
    })
  }
}
</script>

<style scoped>

</style>