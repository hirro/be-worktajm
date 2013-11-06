//
//  wtajmMainViewController.h
//  WorkTajm
//
//  Created by Jim Arnell on 06/11/13.
//  Copyright (c) 2013 Jim Arnell. All rights reserved.
//

#import "wtajmFlipsideViewController.h"

@interface wtajmMainViewController : UIViewController <wtajmFlipsideViewControllerDelegate, UIPopoverControllerDelegate>

@property (strong, nonatomic) UIPopoverController *flipsidePopoverController;

@end
