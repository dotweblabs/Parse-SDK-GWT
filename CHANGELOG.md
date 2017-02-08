## 0-SNAPSHOT - 02/08/17
- Added ParseRole
- Added ParseACL

## 0-SNAPSHOT - 01/26/17
- Password reset request

## 0-SNAPSHOT - 01/25/17
- Checks on ParseResponse

## 0-SNAPSHOT - 01/21/17
- Update user function
- Logout function
- Implemented retrieveCurrentUser function
- Store tokens in localstorage
- Added GWT elemental

## 0-SNAPSHOT - 10/30/16
- Read Parse config function
- Create/Update Parse config function
- Put parse URL into TestKeys
- Added empty constructor for `Where`

## 0-SNAPSHOT - 10/27/16
- Added Parse.Users.become function to login with sessionToken
- Fix failing test `testCreateObject`

## 0-SNAPSHOT - 10/07/16
- Added options

## 0-SNAPSHOT - 09/23/16
- Added getRelation with filtering

## 0-SNAPSHOT - 09/15/16
- Edit Order function

## 0-SNAPSHOT - 09/15/16
- Edit Skip function
- Added limit function
- Added Skip function

## 0-SNAPSHOT - 09/01/16
- Added `FunctionTest`
- Added Run Cloud Code function
- Updated Server URL

## 0-SNAPSHOT - 08/24/16
- Added Logout function exception handling 

## 0-SNAPSHOT - 08/23/16
- Added `relation` method on ParseObject
- Added Order on Parse.Query

## 0-SNAPSHOT - 08/19/16
- Edit Subscription

## 0-SNAPSHOT - 08/19/16
- Added method to create Relation
- Added JUnit test for `createRelation`
- Added `ParseRelation` object
- Added Relation Query

## 0-SNAPSHOT - 08/18/16
- Subscrioption to LiveQuery 

## 0-SNAPSHOT - 08/15/16
- Rollback GWT version to `2.8.0-beta1`
- Removed unused codes 
- Updated GWT version

## 0-SNAPSHOT - 08/12/16
- Added `ParseGeoPoint` object
- Null check on `setObjectId` for `ParseObject`
- Clone `ParseResponse` as `ParseObject` method

## 0-SNAPSHOT - 08/12/16

- Refactor package from `com.dotweblabs.gwt` to `com.parse.gwt`

## 0-SNAPSHOT - 08/09/16

- Add ParsePointer

## 0-SNAPSHOT - 07/03/16

- Fixed for if `Where` is null should not cause query to fail
- Fixed DELETE for Objects
- Added Session token support

## 0-SNAPSHOT - 07/02/16

- Added null check on getter for ParseResponse
- Added `testQueryPointer` Test
- Added null check on ParseObject getters for createdAt and updatedAt

## 0-SNAPSHOT - 07/01/16

- Removed unused package

## 0-SNAPSHOT - 06/30/16

- Added Logout function

### Fixed
- Fixed getErrorCode

### Added
- Signup and login JUnit tests
- Parse signup and login function
- Stub method of Login
- Initialize with App Id and Rest API key

### Fixed 
- Added missing `Parse.Objects.retrieve` function
- Fiexed `testRetrieveObject` Test 

### Updated
- Updated test timeout
- Updated README

## 0-SNAPSHOT - 06/29/16

### Added
- Added Query function and added tests
- Added $or and $and for Where object
- Where Object
- Update ParseObject function
- Delete ParseObject by objectId
- Create ParseObject and save to parse-server
- Initial commit
