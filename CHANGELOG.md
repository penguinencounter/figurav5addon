## 1.1.2

### Parser Changes
- Further small size improvements
  - Discard `chld` tag for parts with no children
- Prevented load from failing if vectors contain things that aren't numbers, including `null` (fallback to `0`)

## 1.1.1

### Parser Changes
- Parity with upstream PR.
- Might reduce your avatar size by a few bytes.
  - fixed detection of default bezier timing elision

## 1.1.0

### Loading Errors
- Fixed an issue with Fabric 1.21.5+ which caused loading to fail when cascading visibilities. 

### Support Changes
- Fabric now has 2 JARs.

## 1.0.0
Initial release.

Support for:
- Fabric: `1.18.2` until `1.21.8` (1 jar)
- Forge: `1.18.2` until `1.21.4` (2 jars)
- NeoForge: `1.20.2` until `1.21.4` (1 jar)