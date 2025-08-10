# Homebrew Formula for Kando CLI
# To publish: Create a tap repository and add this formula

class Kando < Formula
  desc "Kando CLI - Local Kanban for Projects"
  homepage "https://github.com/yuvinraja/kando-cli"
  url "https://github.com/yuvinraja/kando-cli/releases/download/v1.0.0/kando-1.0.0-dist.tar.gz"
  sha256 "REPLACE_WITH_ACTUAL_SHA256"
  license "MIT"

  depends_on "openjdk@17"

  def install
    libexec.install "lib/kando.jar"
    
    # Create wrapper script
    (bin/"kando").write <<~EOS
      #!/bin/bash
      exec "#{Formula["openjdk@17"].opt_bin}/java" -jar "#{libexec}/kando.jar" "$@"
    EOS
  end

  test do
    assert_match "kando", shell_output("#{bin}/kando --version")
  end
end

# Installation instructions for users:
# 1. Create a tap: brew tap yuvinraja/kando
# 2. Install: brew install kando
# 
# To create the tap repository:
# 1. Create repository: yuvinraja/homebrew-kando
# 2. Add this file as: Formula/kando.rb
# 3. Update the SHA256 hash after each release
