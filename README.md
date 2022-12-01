# PixelCloud

## Features

- Module-System
- Template System
- Powerful API
- Support for Spigot, BungeeCord and Velocity
- MySQL support

## TODO

- ~~Multi-Root~~
- ~~Dashboard~~

## Installation
```bash
wget https://download.haizon.eu/cloud/1.0.0.zip
```

## Module

```java
@Module(name = "test-module", authors = "Haizoooon", reloadable = true)
public class ProxyModule implements ICloudModule {

    public void onInitialization(ICloudLogger loggerProvider) {
        //TODO: Called when the module has been loaded
    }
    
    public void onReload(){
        //TODO: called when the reload command was executed
    }
    
}
```
