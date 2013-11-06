//
//  wtajmFlipsideViewController.m
//  WorkTajm
//
//  Created by Jim Arnell on 06/11/13.
//  Copyright (c) 2013 Jim Arnell. All rights reserved.
//

#import "wtajmFlipsideViewController.h"

@interface wtajmFlipsideViewController ()

@end

@implementation wtajmFlipsideViewController

- (void)awakeFromNib
{
    self.preferredContentSize = CGSizeMake(320.0, 480.0);
    [super awakeFromNib];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Actions

- (IBAction)done:(id)sender
{
    [self.delegate flipsideViewControllerDidFinish:self];
}

@end
