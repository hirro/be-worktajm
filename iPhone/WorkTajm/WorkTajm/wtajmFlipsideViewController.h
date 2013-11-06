//
//  wtajmFlipsideViewController.h
//  WorkTajm
//
//  Created by Jim Arnell on 06/11/13.
//  Copyright (c) 2013 Jim Arnell. All rights reserved.
//

#import <UIKit/UIKit.h>

@class wtajmFlipsideViewController;

@protocol wtajmFlipsideViewControllerDelegate
- (void)flipsideViewControllerDidFinish:(wtajmFlipsideViewController *)controller;
@end

@interface wtajmFlipsideViewController : UIViewController

@property (weak, nonatomic) id <wtajmFlipsideViewControllerDelegate> delegate;

- (IBAction)done:(id)sender;

@end
