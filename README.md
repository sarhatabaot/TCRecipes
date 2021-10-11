# TCRecipes

Add custom recipes for cards. Can be used to "upgrade" cards to the next tier.

Included a default file with an example, see below:


```yaml
key: "zombie_to_blaze"
result: "uncommon,blaze,1,false"
recipe:
  - "common,zombie,4,false"
```

## Adding a recipe
To add a recipe, create a new file, yourname.yml with the format above.

**Storage Entry** - The format we use to store card information. (the same as the deck storage format.)
An entry is split into 4 parts. 

rarity, card, amount, shiny in that order.

**Key** - Must be a unique name. 
**Result** - The card result you'd like.
**Recipe** - The recipe to get the result.


 
