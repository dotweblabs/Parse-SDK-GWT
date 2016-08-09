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
