<template>
    <b-container>
        <b-row>
            <b-col>
                <b-card class="rounded-0 border-0 shadow-sm" header="Login">
                    <b-card-body>
                        <b-row class="justify-content-center">
                            <b-form @submit.stop.prevent style="min-width: 30rem">
                                    <b-form-group label="Username">
                                        <b-form-input ref="username" id="username" type="text" v-model="username" required autofocus></b-form-input>
                                    </b-form-group>
                                    <b-form-group label="Password">
                                        <b-form-input ref="password" id="password" type="password" class="form-control" v-model="password" required></b-form-input>
                                    </b-form-group>
                                <b-form-row class="justify-content-end">
                                    <b-button class="btn-primary" type="submit" v-on:click="(e)=>handleSubmit(e)">
                                        Login
                                    </b-button>
                                </b-form-row>
                            </b-form>
                        </b-row>
                    </b-card-body>
                </b-card>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                username:"",
                password:""
            };
        },
        methods: {
            handleSubmit(e) {
                e.preventDefault();
                if (this.password.length > 0 && this.username.length > 0) {
                    let username = this.username;
                    let password = this.password;

                    window.axios.post('api/login', {username, password}).then(res => {
                        let data = res.data.token;
                        let json = atob(data.split('.')[1]);
                        let decodedToken = JSON.parse(json);
                        let isAdmin = decodedToken.roles.includes("ADMIN") || decodedToken.roles.includes("ROLE_ADMIN");

                        localStorage.setItem('recipe-site.user', JSON.stringify(decodedToken));
                        localStorage.setItem('recipe-site.jwt', res.data.token);

                        if (localStorage.getItem('recipe-site.jwt') != null) {
                            this.$emit('loggedin');
                            if (this.$route.params.nextUrl != null) {
                                this.$router.push(this.$route.params.nextUrl);
                            } else {
                                this.$router.push((isAdmin === true ? {name: 'admin'} : {name: 'index'}));
                            }
                        }
                    });
                }
            }
        }
    }
</script>

<style scoped>

</style>
