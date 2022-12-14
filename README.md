# **CREATIGE**


## Final Demo


<img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/demos/FinalDemo.gif" width=450><br>


## Table of Contents

1. [Overview](#Overview)
2. [Product Spec](#Product-Spec)
3. [Wireframes](#Wireframes)
4. [Schema](#Schema)

## Overview

### Description

 Creatige App allows the user to create pictures using Stable Diffusion, a cutting edge generative AI system. It takes a description of a picture as an input, and produces completely novel and unique artwork. The app uses an open source API to produce the images, and makes it easy for the user to save their work, share it, as well as get inspiration from other users and provide feedback.

The advent of generative AIs is estimated to be [no short of a revolution](https://stratechery.com/2022/the-ai-unbundling/). Creatige will make this new technology available to more people.

### App Evaluation

- **Category:** AI art generator.

- **Mobile:** This app is more than a glorified web application because it is capable of accepting images as an input, so the user can take a picture with their camera and use it as a base for generating art. The app also uses push notifications to inform the user their images are ready, as well as other functions like having an image of theirs added as a favorite by another user.
 
- **Story:** This app is of immense value to its audience, because it allows users to use this cutting edge technology on the go, in a simple and efficient way. It can be useful to both artists and enthusiasts. 
 
- **Market:** Stable Diffusion is extremely new and the user base is growing day by day. With this app, more users will be able to use the system.
 
- **Habit:** The app is primarily about creating, allowing the user to express themselves via art, regardless of their traditional art skills. Generating art is very addictive. The user could spend hours on end with it.
 
- **Scope:** A stripped down version of the app would still be interesting to build because it uses an API for image generation, as well as camera integration, and remote image storage in our backend. A fully fledged future version of the app will include more advanced social interaction, as well as advanced features, such as inpainting.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [x] User can log into the app

- [x] User can create an account 

- [x] Create a stream to view creations posted on the app 

- [x] The current signed in user is persisted across app restarts 

- [x] The user profile shows the user???s creations

- [x] User can tap on a post and it will display a detailed view of that post

- [x] User will be able to create AI generated pictures using a text description on the creation screen

- [x] User will be able to create AI generated pictures using a text description and an initialization image taken with the phone camera on the creation screen

- [x] Detail view shows comments

- [x] Detail view lets user add comments

- [x] User can log out of the app

- [x] User can change their profile picture
 
- [x] User can change their password


**Optional Nice-to-have Stories**

Feed

 - [x] Make profile pictures circular
 - [x] Pull to refresh
 - [x] Make image take whole width of screen 
 - [x] Implement search function, by user
 - [x] User can delete posts
 - [x] Delete refreshes feed

Detail

 - [x] Make profile pictures circular
 - [x] Make image take all width of screen
 - [x] Show prompt
 - [x] Show negative prompt
 - [x] Allow user to favorite a post (the Favorites view should be implemented in Feed for this to be evident)
 - [x] Show number of times a post was added to favorites
 - [x] Allow user to delete post
 - [x] Allow user to download post
 - [x] delete finishes the Detail activity, goes back to Feed, and refreshes it

Create

 - [x] Add progressbar
 - [x] Create thread to continue generating in background when navigating away from fragment
 - [x] Show the values of the sliders in TextViews next to them
 - [x] Auto hide keyboard after pressing "Create" button and make EditText lose focus

Profile

 - [x] Pull to refresh
 - [x] Delete should also refresh recyclerview to show the deletion
 - [x] Long press to delete
 - [x] Make profile pictures circular

### 2. Screen Archetypes

* Login
  * User can log into the app
  
* Register
  * User can create an account
  
* Feed
  * Create a stream to view creations posted on the app
  * User can tap on a post and it will display a detailed view of that post 
  
* Detail
  * Shows the picture embedded in the post
  * Shows comments
  * User can add comments
  
* Create
  * User will be able to create AI generated pictures using a text description
  * User will be able to create AI generated pictures using a text description and an initialization image taken with the phone camera
  * User can use advanced settings for creation
  
* Profile
  * The user profile shows the user???s creations
  
* Settings
  * User can log out of the app
  * User can change their profile picture
  * User can change their password
  * User can change the visibility of their profile

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Feed
* Create
* Profile

**Flow Navigation** (Screen to Screen)

* Login -> Feed (on successful login)
* Login -> Register (if user is not registered)
* Register -> Feed
* Feed -> Detail
* Profile -> Settings
* Settings -> Login (if user logs out in Settings)

## Wireframes

### Digital Wireframes & Mockups

#### **[Lo-Fi]**

#### Login

<img src='others\pictures\wireframes\lo-fi\Login.png' height='500'/>
<br><br>

#### Register

<img src='others\pictures\wireframes\lo-fi\Register.png' height='500'/>
<br><br>

#### Feed

<img src='others\pictures\wireframes\lo-fi\Feed.png' height='500'/>
<br><br>

#### Detail

<img src='others\pictures\wireframes\lo-fi\Detail.png' height='500'/>
<br><br>

#### Create

* Text to image

<img src='others\pictures\wireframes\lo-fi\Create.png' height='500'/>
<br>

* Image to image

<img src='others\pictures\wireframes\lo-fi\Create-img.png' height='500'/>
<br><br>

#### Profile

<img src='others\pictures\wireframes\lo-fi\Profile.png' height='500'/>
<br><br>

#### Settings

<img src='others\pictures\wireframes\lo-fi\Settings.png' height='500'/>
<br><br>

### 



#### **[Hi-Fi]**

#### Login

<img src='others\pictures\wireframes\hi-fi\Login.png' height='500'/>
<br>

#### Register

<img src='others\pictures\wireframes\hi-fi\Register.png' height='500'/>
<br>

#### Feed

<img src='others\pictures\wireframes\hi-fi\Feed.png' height='500'/>
<br>

#### Detail

<img src='others\pictures\wireframes\hi-fi\Detail.png' height='500'/>
<br>

#### Create

<img src='others\pictures\wireframes\hi-fi\Create-txt2img.png' height='500'/>
<br>

<img src='others\pictures\wireframes\hi-fi\Create-img2img.png' height='500'/>
<br>

#### Profile

<img src='others\pictures\wireframes\hi-fi\Profile.png' height='500'/>
<br>

#### Settings

<img src='others\pictures\wireframes\hi-fi\Settings.png' height='500'/>
<br>

### 

### Interactive Prototype

<img src='others\pictures\wireframes\interactive.gif' height='500'/>
<br><br>

## Schema

### Models

#### User

| Property      | Type     | Description |
| ------------- | -------- | ------------|
| objectId      | String   | unique id for the user post (default field) |
| emailVerified | Boolean  | if this user is email verified |
| image         | File     | image that user posts |
| caption       | String   | image caption by author |
| commentsCount | Number   | number of comments that has been posted to an image |
| likesCount    | Number   | number of likes for the post |
| createdAt     | DateTime | date when post is created (default field) |
| updatedAt     | DateTime | date when post is last updated (default field) |


#### Post

| Property      | Type     | Description |
| ------------- | -------- | ------------|
| objectId      | String   | unique id for the user post (default field) |
| author        | Pointer to User| image author |
| image         | File     | image that user posts |
| prompt        | String   | prompt to generate image |
| commentsCount | Number   | number of comments that has been posted to an image |
| comments      | Array    | array holding the comments and usernames|
| likesCount    | Number   | number of likes for the post |
| createdAt     | DateTime | date when post is created (default field) |

#### Comments
| Property      | Type     | Description |
| ------------- | -------- | ------------|
| objectId      | String   | unique id for the user post (default field) |
| author        | Pointer to User| image author |
| comment       | String   | Comment the user posted on the post |
| Post          | Pointer  | pointer to post |


### Networking
#### List of network requests by screen:

* **Feed Screen**

  * (Read/GET) Query all posts <br>
  <br>


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/GetAllPosts.png" width=500><br><br>
    
* **Create Post Screen**


  * (Create/Post) Create new post on the feed screen <br>
  <br>


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/CreatePost.png" width=500><br><br>
  
* **Profile Screen**


  * (Read/GET) Get user's post <br>
  <br>


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/PostsByUser.png" width=500><br><br>
  
* **Detail Screen**
 
 
  * (Read/GET) Get user comments for the specific post <br>
  <br>


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/queryCommentsByPost.png" width=500><br><br>
 
 
  * (Create/POST) Get user comments for the specific post <br>
  <br>


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/createComment.png" width=500><br><br>
 
 
* **Login Screen**


  *  (Read/GET) Pass the username and password in URL parameters <br>
  <br>


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/LoginUser.png" width=500><br><br>
 
 
* **Register Screen**


  * (Create/POST) Create new user with username and password <br>
  <br>


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/RegisterUser.png" width=500><br><br>
  
* **Settings Screen**


  * (Update/PUT) Update user profile picture <br>
  <br>


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/updateUserAvatar.png" width=500><br><br>
  
  
  * (Update/PUT) Update users password after verifying the password <br>
  <br>


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/updateUserPassword.png" width=500><br>
  
  ## Sprint 1
  
  
  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/demos/Sprint1Demo.gif" width=500><br>

  ## Sprint 2


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/demos/Sprint2Demo.gif" width=500><br>

  ## Sprint 3

  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/demos/Sprint3Demo.gif" width=500><br>
  
  ## Sprint 4
  
  
  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/demos/Sprint4Demo.gif" width=500><br>
