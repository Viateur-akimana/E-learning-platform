package org.example.restapi.controller;
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserInfoService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/public/welcome")
    public String welcome() {
        return "Welcome! This endpoint is not secure.";
    }

    @PostMapping("/public/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to the User Profile!";
    }

    @GetMapping("/user/profile/{username}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Optional<UserInfo> userProfileByUsername(@PathVariable("username") String username) {
        System.out.println(username);
        return userService.getUserByUsername(username);
    }

    @PostMapping("/public/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
