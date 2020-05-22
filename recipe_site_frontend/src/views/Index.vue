<template>
    <b-container>
        <b-list-group horizontal>
            <b-list-group-item>
                <b-form inline>
                    <b-form-input type="text" v-model="filterType" placeholder="Type"></b-form-input>
                </b-form>
            </b-list-group-item>
            <b-list-group-item>
                <b-form inline>
                    <b-form-input type="text" v-model="filterAuthorName" placeholder="Author"></b-form-input>
                </b-form>
            </b-list-group-item>
            <b-list-group-item>
                <b-form inline>
                    <b-form-input type="number" v-model="filterRating" placeholder="Difficulty Rating"></b-form-input>
                </b-form>
            </b-list-group-item>
            <b-list-group-item>
                <b-form inline>
                    <b-button variant="primary" class="mr-2" v-on:click="()=>this.resetRecipeFilters()">Reset</b-button>
                    <b-button variant="primary" v-on:click="()=>this.filterRecipes()">Filter</b-button>
                </b-form>
            </b-list-group-item>
        </b-list-group>
        <b-card-group deck>
            <b-card v-if="this.isLoggedIn" class="shadow-sm mb-5 rounded-0 border-0 col-md-3"
                    img-top
                    style="min-width: 18rem;max-width: 319px;"
            >
                <b-card-body class="justify-content-center">
                    <b-button variant="link" class="text-decoration-none" v-on:click="()=>this.showRecipeModal()">
                            Add new recipe
                    </b-button>
                </b-card-body>
            </b-card>
            <b-card v-for="recipe in recipes" :key="recipe.uuid" class="shadow-sm mb-5 rounded-0 border-0 col-md-3"
                img-top
                v-bind:img-src="'/api/image/'+recipe.imageName"
                style="min-width: 18rem;max-width: 319px;"
            >
                <b-card-body>
                    <b-button variant="link" class="text-decoration-none" :to="{name: 'recipe-details', params: { id: recipe.uuid }}">
                        <p>
                            {{recipe.title}}
                        </p>
                    </b-button>
                    <b-form-rating :v-model="recipe.difficultyRating"
                                   show-value
                                   show-value-max
                                   precision="1"
                                   no-border
                                   readonly v-model="recipe.difficultyRating">
                    </b-form-rating>
                </b-card-body>
            </b-card>
        </b-card-group>
        <b-modal id="addNewRecipeModal" v-model="this.showModal" v-on:hide="()=>this.onModalHide()" :ok-disabled="true" :cancel-disabled="true">
            <b-row class="justify-content-center">
                <b-form>
                    <b-form-group label="Title">
                        <b-form-input v-model="newRecipetitle" name="newRecipetitle">
                        </b-form-input>
                    </b-form-group>
                    <b-form-group label="Content">
                        <b-form-textarea v-model="newRecipecontent" name="newRecipecontent">
                        </b-form-textarea>
                    </b-form-group>
                    <b-form-group label="Type">
                        <b-form-input type="text" v-model="newRecipetype" name="newRecipetype">
                        </b-form-input>
                    </b-form-group>
                    <b-form-group label="Difficulty rating">
                        <b-form-rating v-model="newRecipedifficultyRating" name="newRecipedifficultyRating" show-value
                                       show-value-max
                                       precision="1"
                                       no-border>
                        </b-form-rating>
                    </b-form-group>
                    <b-form-group label="Photo">
                        <b-form-file type="file" id="image" name="image" v-on:input="(file)=>this.uploadFile(file)">
                        </b-form-file>
                    </b-form-group>
                        <b-list-group>
                            <b-list-group-item v-for="ingredient in this.newRecipeingredients" v-bind:key="ingredient">{{ingredient}}</b-list-group-item>
                        </b-list-group>
                    <b-form-group label="Ingredients">
                        <b-form-input type="text" v-model="ingredient">
                        </b-form-input>
                        <b-button variant="link" v-on:click="()=>this.addIngredient()"><font-awesome-icon :icon="['fas','plus']"></font-awesome-icon></b-button>
                    </b-form-group>
                    <b-form-row class="justify-content-end">
                        <b-button variant="primary" v-on:click="()=>this.addNewRecipe()">Save</b-button>
                    </b-form-row>
                </b-form>
            </b-row>
        </b-modal>
    </b-container>
</template>

<script>
    export default
    {
        name: "Index",
        data(){
            return{
                recipes: [],
                isLoggedIn:false,
                isAdmin:false,
                newRecipetitle:"",
                newRecipecontent:"",
                newRecipeingredients: [],
                newRecipetype:"",
                newRecipedifficultyRating: 0,
                newRecipeimageName:"",
                ingredient:"",
                showModal: false,
                filterType:"",
                filterAuthorName:"",
                filterRating:""
            };
        },
        beforeMount(){
            this.loadRecipes();
            let user = JSON.parse(localStorage.getItem('recipe-site.user'));
            if(user){
                this.isLoggedIn=true;
                console.log(user);
                if(user.roles.includes("ADMINISTRATOR") || user.roles.includes("ROLE_ADMINISTRATOR")){
                    this.isAdmin = true;
                }
            }
            else{
                this.isLoggedIn=false;
            }
        },
        methods : {
            resetRecipeFilters(){
                this.filterType="";
                this.filterAuthorName="";
                this.filterRating="";
                this.filterRecipes()
            },
            filterRecipes(){
                let query ="";
                if(this.filterType)
                {
                    query+="type="+this.filterType;
                }
                if(this.filterAuthorName)
                {
                    query+="authorName="+this.filterAuthorName;
                }
                if(this.filterRating !== null && this.filterRating !== -1)
                {
                    query+="rating="+this.filterRating;
                }
                window.axios.get('api/recipe?'+query).then(res => {
                    this.recipes = res.data;
                }).catch(()=>console.log("error loading recipe data"));
            },
            loadRecipes(){
                window.axios.get('api/recipe').then(res => {
                    this.recipes = res.data;
                }).catch(()=>console.log("error loading recipe data"));
            },
            uploadFile(file){
                let formData = new FormData();
                formData.append("image", file);
                let headers = {'Content-Type': 'multipart/form-data'};
                window.axios.post('/api/image/upload',formData,{headers}).then(response => {
                    this.newRecipeimageName = response.data;
                });
            },
            addIngredient(){
                this.newRecipeingredients.push(this.ingredient);
                this.ingredient="";
            },
            showRecipeModal(){
                this.showModal=true;
            },
            addNewRecipe(){
                window.axios.post('/api/recipe/',{
                    title:this.newRecipetitle,
                    content:this.newRecipecontent,
                    ingredients:this.newRecipeingredients,
                    type:this.newRecipetype,
                    difficultyRating:this.newRecipedifficultyRating,
                    imageName:this.newRecipeimageName
                });
                //this.loadRecipes();
                this.showModal=false;
            },
            onModalHide(){
                this.showModal=false;
            }
        },
    }
</script>

<style scoped>

</style>
