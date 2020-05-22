<template>
    <b-container>
        <b-overlay v-if="this.loadingComments && this.loadingRecipe" :show="true" no-wrap>
        </b-overlay>
        <b-card v-else>
            <b-card-title>{{recipe.title}}</b-card-title>
            <b-card-sub-title>{{recipe.type}}</b-card-sub-title>
            <b-card-img v-bind:src="'/api/image/'+recipe.imageName" img-alt="Card image" img-top class="border-0 rounded-0 shadow-sm"></b-card-img>
            <b-button v-b-toggle.collapse-1 variant="link"><h4>Ingredients</h4></b-button>
            <b-collapse id="collapse-1" class="mt-2">
                <b-list-group>
                    <b-list-group-item v-for="ingredient in this.recipe.ingredients" v-bind:key="ingredient">
                        {{ingredient}}
                    </b-list-group-item>
                </b-list-group>
            </b-collapse>
            <b-card-body>
                <b-card-text>
                    <p>
                    {{recipe.content}}
                    </p>
                </b-card-text>
                <b-button variant="link" class="text-danger-on-hover" v-on:click="()=>removeRecipe()" v-if="isAdmin"><font-awesome-icon :icon="['fas','trash']"></font-awesome-icon></b-button>
            </b-card-body>
            <b-list-group class="mt-5">
                <b-list-group-item  v-for="comment in comments" v-bind:key="comment.uuid">
                    <h6>{{comment.authorUsername}}</h6>
                    <p>{{comment.commentText}}</p>
                    <b-button variant="link" class="text-danger-on-hover" v-on:click="()=>removeComment(comment.uuid)" v-if="isAdmin"><font-awesome-icon :icon="['fas','trash']"></font-awesome-icon></b-button>
                </b-list-group-item>
            </b-list-group>
            <b-card-footer v-if="this.isLoggedIn">
                <b-form>
                    <b-form-group label="New comment: ">
                        <b-form-textarea id="commenttext"
                                 rows="3"
                                 no-resize
                                 v-model="commentText"
                                 :state="commentText.length>=0 && commentText.length <= 500"
                        >
                        </b-form-textarea>
                    </b-form-group>
                    <b-form-group>
                        <b-button type="primary" v-on:click="()=>this.addComment()">Send</b-button>
                    </b-form-group>
                </b-form>
            </b-card-footer>
        </b-card>
    </b-container>
</template>

<script>
    export default {
        name: "Details",
        data()
        {
            return{ recipe: null, id: null, comments: [], isLoggedIn: false, isAdmin: false, commentText: "", loadingRecipe:true, loadingComments: true};
        },
        beforeMount()
        {
            this.loadRecipe();
            this.loadComments();
            let user = JSON.parse(localStorage.getItem('recipe-site.user'));
            if(user)
            {
                this.isLoggedIn=true;
                if(user.roles.includes("ROLE_ADMINISTRATOR") || user.roles.includes("ADMINISTRATOR") )
                {
                    this.isAdmin = true;
                }
            }
            else
            {
                this.isLoggedIn=false;
            }
        },
        methods : {
            addComment()
            {
                window.axios.post('/api/recipe/comment',{commentText:this.commentText,recipeUuid:this.id}).then(res => {
                    this.recipe = res.data;
                }).catch(()=>console.log("error loading recipe data"));
            },
            removeComment(uuid)
            {
                window.axios.delete('/api/recipe/comment/'+uuid).then(()=>this.loadComments()).catch(()=> {
                    console.log("error deleting comment");
                    this.loadingRecipe=false;
                });
            },
            removeRecipe()
            {
                window.axios.delete('/api/recipe/'+this.id).then(() => {
                    this.$router.push({name:"index"});
                }).catch(()=>console.log("error deleting recipe"));
            },
            loadRecipe()
            {
                this.id = this.$route.params.id;
                window.axios.get('/api/recipe/'+this.id).then(res => {
                    this.recipe = res.data;
                    this.loadingRecipe=false;
                }).catch(()=>console.log("error loading recipe data"));
            },
            loadComments()
            {
                window.axios.get('/api/recipe/'+this.id+'/comments').then(res => {
                    this.comments = res.data;
                    this.loadingComments=false;
                }).catch(()=>{
                    console.log("error loading recipe comments");
                    this.loadingComments=false;
                });
            }
        }
    }
</script>

<style scoped>
    .text-danger-on-hover:hover
    {
        color:#e3342f;
    }
    .text-danger-on-hover
    {
        color:#6c757d;
    }
</style>
