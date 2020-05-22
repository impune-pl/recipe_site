<template>
  <div>
    <b-navbar type="light" class="shadow-sm justify-content-between">
      <b-navbar-brand :to="{name:'index'}">
        Recipe site
      </b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav class="justify-content-end">
          <b-nav-item v-if="isLoggedIn && userType" :to="{name: 'admin'}">Admin</b-nav-item>
          <b-nav-item-dropdown v-if="isLoggedIn" right>
            <template v-slot:button-content>
              Hi, {{name}}
            </template>
            <b-dropdown-item v-on:click="logout()">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item v-if="!isLoggedIn" :to="{name: 'login'}" right>Log In</b-nav-item>
          <b-nav-item v-if="!isLoggedIn" :to="{name: 'register'}" right>Register</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <main class="py-4">
      <router-view v-on:loggedin="change"></router-view>
    </main>
  </div>
</template>
<script>
  export default {
    name:"App",
    data(){
      return {
        name        : "",
        userType   : 0,
        isLoggedIn  : localStorage.getItem('recipe-site.jwt') != null
      }
    },
    mounted() {
      this.setDefaults()
    },
    methods : {
      setDefaults(){
        if(this.isLoggedIn){
          let user        = JSON.parse(localStorage.getItem('recipe-site.user'));
          this.name       = user.name;
          this.userType  = user.roles.includes("ADMIN");
        }
      },
      change(){
        this.isLoggedIn = localStorage.getItem('recipe-site.jwt') != null;
        this.setDefaults();
      },
      logout(){
        localStorage.removeItem('recipe-site.jwt');
        localStorage.removeItem('recipe-site.user');
        this.change();
        this.$router.push('/');
      }
    }
  }
</script>

<style scoped>

</style>
