﻿using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class ImageDisplayViewController : UIViewController, IFileDisplayScreenletDelegate
    {
        public ImageDisplayViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.imageDisplayScreenlet.AutoLoad = false;
            this.imageDisplayScreenlet.AssetEntryId = 54500;

            this.imageDisplayScreenlet.Delegate = this;
            this.imageDisplayScreenlet.Load();
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

        /* IFileDisplayScreenletDelegate */

        [Export("screenlet:onFileAssetError:")]
        public virtual void OnFileAssetError(FileDisplayScreenlet screenlet, NSError error)
        {
            System.Diagnostics.Debug.WriteLine($"Image display failed: {error.DebugDescription}");
        }

        [Export("screenlet:onFileAssetResponse:")]
        public virtual void OnFileAssetResponse(FileDisplayScreenlet screenlet, NSUrl url)
        {
            System.Diagnostics.Debug.WriteLine($"Image display succesful: {url.DebugDescription}");
        }
    }
}

