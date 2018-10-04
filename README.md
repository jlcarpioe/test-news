# TEST NEWS

This repository contains the development source code of app to show list of news.

* MinSdkVersion 22
* Tested on Android 8.0


### Settings

Configuration of API's URL

```
<meta-data android:name="base_url" android:value="https://hn.algolia.com/" />
<meta-data android:name="prefix_url" android:value="api/v1/" />
```

### Features

* List of recent post order by date.
* Search new posts when reach the end of the list.
* Refresh list when pull down on top.
* Show content of post in a web view.
* Swipe on post to delete.

