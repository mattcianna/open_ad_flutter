#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run 'pod lib lint open_ad_flutter.podspec' to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'open_ad_flutter'
  s.version          = '0.0.1'
  s.summary          = 'A new flutter plugin project.'
  s.description      = <<-DESC
A new flutter plugin project.
                       DESC
  s.homepage         = 'https://github.com/kmcgill88/admob_flutter'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Kevin McGill' => 'kevin@mcgilldevtech.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  # https://firebase.google.com/docs/ios/setup
  s.dependency 'Google-Mobile-Ads-SDK', '~> 7.64'
  s.ios.deployment_target = '9.0'
  s.static_framework = true
end
