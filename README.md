# Luck's Showcase

This mod adds in neat ways of displaying your items in Minecraft

## Getting Started

- Click "Use this template" on GitHub to create a new repository based on this template.
- Clone the repository to your local machine.
- Open the project in IntelliJ IDEA.
- Open `gradle.properties` and configure your mod's metadata.
- Now that you've set a mod id, you should rename all instances of `yourmod` and `YourMod` to your mod id / name, both
  in code and in file names.
- The majority of code lies in the `common` folder, while the mod-loader specific folders are only needed for
  implementing third party mod compatibility.
- The template includes a basic example for most of Balm's features. Delete parts that you don't need before releasing
  your mod.
- Don't forget to replace your mod's logo in `common/src/main/resources/yourmod-icon.png`.

If you need to add dependencies, do so in `dependencies.gradle`. I recommend leaving the `build.gradle` files untouched
to make updating to newer versions of the template easier.

### CHANGELOG.md
