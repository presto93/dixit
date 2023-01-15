<template>

  <v-btn
      @click="ready"
      small
  >
    ready
  </v-btn>
</template>

<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
  name: "HomeView",
  components: {},
  created() {
  },
  data() {
    return {
    }
  },
  computed: {},
  methods: {
    ready() {
    },
  },
})
</script>

<style scoped>

</style>