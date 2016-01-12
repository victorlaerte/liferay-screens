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


class ScreensPasscodeLockPresenter: PasscodeLockPresenter {

	init(mainWindow window: UIWindow?, configuration: PasscodeLockConfigurationType) {
		let passcodeLockVC = PasscodeLockViewController(state: .EnterPasscode, configuration: configuration)

		super.init(mainWindow: window, configuration: configuration, viewController: passcodeLockVC)

		NSNotificationCenter.defaultCenter().addObserver(self,
			selector: "applicationDidLaunched",
			name: UIApplicationDidFinishLaunchingNotification,
			object: nil)
		
		NSNotificationCenter.defaultCenter().addObserver(self,
			selector: "applicationDidEnterBackground",
			name: UIApplicationDidEnterBackgroundNotification,
			object: nil)
		
		NSNotificationCenter.defaultCenter().addObserver(self,
			selector: "applicationDidBecomeActive",
			name: UIApplicationDidBecomeActiveNotification,
			object: nil)
	}
	
	deinit {
		NSNotificationCenter.defaultCenter().removeObserver(self)
	}
	
	dynamic func applicationDidLaunched() -> Void {
		presentPasscodeLock()
	}
	
	dynamic func applicationDidEnterBackground() -> Void {
		presentPasscodeLock()
	}
	
	dynamic func applicationDidBecomeActive() -> Void {
	}
	
}