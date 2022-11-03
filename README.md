Original App Design Project - README Template
===
# CREATIGE

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description

Creatige App allows the user to create pictures using Stable Diffusion, a cutting edge generative AI system. It takes a description of a picture as an input, and produces completely novel and unique artwork. The app uses an open source API to produce the images, and makes it easy for the user to save their work, share it, as well as get inspiration from other users and provide feedback.

The advent of generative AIs is estimated to be [no short of a revolution](https://stratechery.com/2022/the-ai-unbundling/). Creatige will make this new technology available to more people.


### App Evaluation

**Category:** AI art generator.

**Mobile:** This app is more than a glorified web application because it is capable of accepting images as an input, so the user can take a picture with their camera and use it as a base for generating art. The app also uses push notifications to inform the user their images are ready, as well as other functions like having an image of theirs added as a favorite by another user.

**Story:** This app is of immense value to its audience, because it allows users to use this cutting edge technology on the go, in a simple and efficient way. It can be useful to both artists and enthusiasts. 

**Market:** Stable Diffusion is extremely new and the user base is growing day by day. With this app, more users will be able to use the system.

**Habit:** The app is primarily about creating, allowing the user to express themselves via art, regardless of their traditional art skills. Generating art is very addictive. The user could spend hours on end with it.

**Scope:** A stripped down version of the app would still be interesting to build because it uses an API for image generation, as well as camera integration, and remote image storage in our backend. A fully fledged future version of the app will include more advanced social interaction, as well as advanced features, such as inpainting.

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
* User receives push notifications
* User can view the embedded image media within the detail view
* User can log out of the app
**Optional Nice-to-have Stories**

* Allow user to make their profile public or private 
* Share between other social media
* Allow user to search for other accounts in the app 
* User can refresh posts timeline by pulling down to refresh
* User can comment on a post
* User can like a post 
* User can add friends to see their creations on their feed
* User can add posts to their favorites
* User can view a feed of their favorite images

### 2. Screen Archetypes

* Login
  * User can log into the app
* Register
  *  User can create an account
* Feed
  * Create a stream to view creations posted on the app
* Detail
  * User can tap on a post and it will display a detailed view of that post
* Create
  * User will be able to create AI generated pictures using a text description
  * User will be able to create AI generated pictures using a text description and an initialization image taken with the phone camera
* Profile
  * The user profile shows the user’s username and creations
* Settings
  * User can log out of the app


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
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

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

<img src='others\pictures\wireframes\lo-fi\Create.png' height='500'/>
<br><br>

#### Profile

<img src='others\pictures\wireframes\lo-fi\Profile.png' height='500'/>
<br><br>

#### Settings

<img src='others\pictures\wireframes\lo-fi\Settings.png' height='500'/>
<br><br>

### 


### [BONUS] Interactive Prototype

## Schema
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]