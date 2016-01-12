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
	import KeychainAccess
#endif


extension PasscodeRepositoryType {

	var hasPasscode: Bool {
		return (passcode != nil)
	}

}


class KeychainPasscodeRepository: PasscodeRepositoryType {

	private let keychainKey = "liferay.screens.passcode"

	private let keychain = Keychain()

	var passcode: [String]? {
		let passcode = try? keychain.getData(keychainKey).map {
				NSKeyedUnarchiver.unarchiveObjectWithData($0)
			}
		return (passcode as? [String]) ?? nil
	}

	func savePasscode(passcode: [String]) {
		let data = NSKeyedArchiver.archivedDataWithRootObject(passcode)

		do {
			try keychain.set(data, key: keychainKey)
		}
		catch {
			print("[ERROR] Error saving passcode")
		}
	}

	func deletePasscode() {
		do {
			try keychain.remove(keychainKey)
		}
		catch {
			print("[ERROR] Error deleting passcode")
		}
	}

}
