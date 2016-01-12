/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/
import Foundation

#if LIFERAY_SCREENS_FRAMEWORK
	import PasscodeLock
#endif


struct PasscodeLockConfiguration: PasscodeLockConfigurationType {

	let repository: PasscodeRepositoryType
	let passcodeLength = 4
	var isTouchIDAllowed = true
	let shouldRequestTouchIDImmediately = true
	let maximumInccorectPasscodeAttempts = -1

	init() {
		self.repository = KeychainPasscodeRepository()
	}

	init(repository: PasscodeRepositoryType) {
		self.repository = repository
	}
	
}
