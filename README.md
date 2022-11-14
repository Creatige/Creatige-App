# **CREATIGE**

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

* User can log into the app

* User can create an account 

* Create a stream to view creations posted on the app 

* The current signed in user is persisted across app restarts 

* The user profile shows the user’s creations

* User can tap on a post and it will display a detailed view of that post

* User will be able to create AI generated pictures using a text description on the creation screen

* User will be able to create AI generated pictures using a text description and an initialization image taken with the phone camera on the creation screen

* User receives push notifications to notify them their image is ready

* Detail view shows comments

* Detail view lets user add comments

* User can log out of the app

* User can change their profile picture
 
* User can change their password

* User can change the visibility of their profile

**Optional Nice-to-have Stories**

* Share between other social media

* Allow user to search for other accounts in the app 

* User can refresh posts timeline by pulling down to refresh

* User can add friends to see their creations on their feed

* User can add posts to their favorites

* User can view a feed of their favorite images

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
  * The user profile shows the user’s creations
  
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

[This section will be completed in Unit 9]

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
* Feed Screen

  * (Read/GET) Query all posts 


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/GetAllPosts.png" width=200>
    
* Create Post Screen


  * (Create/Post) Create new post on the feed screen


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/CreatePost.png" width=300>
  
* Profile Screen


  * (Read/GET) Get user's post


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/PostsByUser.png" width=300>
  
* Detail Screen


  *  (Read/GET) Get the post from back4app 


 <img src="" width=300>
 
 
  *  (Read/GET) Get user comments for the specific post


 <img src="" width=300>
 
 
* Login Screen


  *  (Read/GET) Pass the username and password in URL parameters


 <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/LoginUser.png" width=300>
 
 
* Register Screen


  * (Create/POST) Create new user with username and password


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/RegisterUser.png" width=300>
  
* Setting Screen


  * (Update/PUT) Update user profile picture


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/updateUserAvatar.png" width=300>
  
  
  * (Update/PUT) Update users password after verifying the password


  <img src="https://github.com/Creatige/Creatige-App/blob/main/others/pictures/networking/updateUserPassword.png" width=300>
