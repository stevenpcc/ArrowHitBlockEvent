ArrowHitBlockEvent
==================

A problem with bukkit is that it's pretty much impossible to get the block that an arrow has hit. This plugin fires an event when a block is hit by an arrow, and also provides the block that was hit. It uses some craftbukkit methods using reflection. Will break on each significant bukkit update.

I made this plugin for my own projects and thought it may be of use to others.

Usage
-------------------------

Just reference the ArrowHitBlockEvent jar and you can use the event in your code

    @EventHandler
    private void onArrowHitBlock(ArrowHitBlockEvent event) {
      Arrow arrow = event.getArrow();
      Block block = event.getBlock(); // the block that was hit
    }

Compilation
-------------------------

* Install [Maven 3](http://maven.apache.org/download.html)
* Clone this repo and: `mvn clean install`