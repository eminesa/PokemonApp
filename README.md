# Orion Innovation Türkiye Mobile Development Assignment

## Description

Develop a Pokédex application that shows all available *Pokémons*. 

## Requirements
App's design with at least 2 screens using the 2 APIs given below that is expected from you in this application.

The expected screens should be as follows;

1. List of Pokémons

   All possible species should be listed on the expected screen and should be selectable.

2. A screen with detailed Pokémon information for a Pokémon selected from the list of species.

    This screen should show pieces of information about the selected Pokémon with the given APIs.

*Apart from these requests, our candidates are completely free in terms of UI & UX and application structure.*


**Create your solution on a git branch, perform your work there, and finish by creating a PR. We will evaluate your work on the PR.** 

**Note: Please don't merge PR directly, we need to review it.**

## Tools

Use the public [Poké-API](https://pokeapi.co) to create the application.

### End Points: 

You can use the following request to load possible species.

- Pokémon species (`v2/pokemon-species`)

	You can use the following request to load images of specified Pokémon.

- Load the species' image using this pattern: `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{species_id}.png`)

	Use *pokemon.id* for get specific pokemon image, `species.id == pokemon.id`

For more information about the API you can check the [API DOC](https://pokeapi.co/docs/v2#pokemon-species).

## Developer Notes

* You do not need to display all the values received from API on the screen. Only the values that you think will be useful or interesting for the user.
* Helper dependencies can be used but the core should be your own code.
* Depending on the platform you can choose any **native programming language** 
 	- iOS: Swift/Objective-C
 	- Android: Kotlin/Java
* Focus on phones (iPhone/Android phones) only. Ensure that implemented UI supports all types of device sizes. The layout shouldn't be broken when the user rotates the device or use a different size of devices.


## How will be assessed?
We will evaluate your application in a few steps. Each step will be evaluated based on its weight score and the candidate will get a total score. Accordingly, the evaluation will result in either positive or negative results.

### 1. Functionality 25 

- Does the app crash? 
- If errors are received during runtime, are these errors handled?

### 2. Quality of code 30

- Is there a preferred code style?
- Is the written code easy to read?
- Is there any code duplication?

### 3. Application Architecture 20
- Which architecture was used? 
- To what extent the rules of the chosen architecture have been used?

### 4. UX & UI 15
- How user-friendly is the application? 
- Is it easy to use?

### 5. Library selection 10
- Which libraries and APIs were used?


## Bonus
Try to approach this as you would approach a production application. Let it support typical situations unique to mobile applications, such as lack of network coverage, etc.

If you have some time to spend beyond the assessment requirements, feel free to improve/extend the app in any way: create an attractive UI, animations, some new functionality, unit tests, etc.

